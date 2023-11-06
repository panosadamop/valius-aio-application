import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('RelatedServiceElements e2e test', () => {
  const relatedServiceElementsPageUrl = '/related-service-elements';
  const relatedServiceElementsPageUrlPattern = new RegExp('/related-service-elements(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const relatedServiceElementsSample = { value: 'Kids Internal Principal', language: 'ENGLISH' };

  let relatedServiceElements;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/related-service-elements+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/related-service-elements').as('postEntityRequest');
    cy.intercept('DELETE', '/api/related-service-elements/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (relatedServiceElements) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/related-service-elements/${relatedServiceElements.id}`,
      }).then(() => {
        relatedServiceElements = undefined;
      });
    }
  });

  it('RelatedServiceElements menu should load RelatedServiceElements page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('related-service-elements');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RelatedServiceElements').should('exist');
    cy.url().should('match', relatedServiceElementsPageUrlPattern);
  });

  describe('RelatedServiceElements page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(relatedServiceElementsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RelatedServiceElements page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/related-service-elements/new$'));
        cy.getEntityCreateUpdateHeading('RelatedServiceElements');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedServiceElementsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/related-service-elements',
          body: relatedServiceElementsSample,
        }).then(({ body }) => {
          relatedServiceElements = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/related-service-elements+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/related-service-elements?page=0&size=20>; rel="last",<http://localhost/api/related-service-elements?page=0&size=20>; rel="first"',
              },
              body: [relatedServiceElements],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(relatedServiceElementsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RelatedServiceElements page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('relatedServiceElements');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedServiceElementsPageUrlPattern);
      });

      it('edit button click should load edit RelatedServiceElements page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RelatedServiceElements');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedServiceElementsPageUrlPattern);
      });

      it('edit button click should load edit RelatedServiceElements page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RelatedServiceElements');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedServiceElementsPageUrlPattern);
      });

      it('last delete button click should delete instance of RelatedServiceElements', () => {
        cy.intercept('GET', '/api/related-service-elements/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('relatedServiceElements').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', relatedServiceElementsPageUrlPattern);

        relatedServiceElements = undefined;
      });
    });
  });

  describe('new RelatedServiceElements page', () => {
    beforeEach(() => {
      cy.visit(`${relatedServiceElementsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RelatedServiceElements');
    });

    it('should create an instance of RelatedServiceElements', () => {
      cy.get(`[data-cy="value"]`).type('red').should('have.value', 'red');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        relatedServiceElements = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', relatedServiceElementsPageUrlPattern);
    });
  });
});

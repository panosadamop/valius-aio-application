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

describe('ConfidenceLevel e2e test', () => {
  const confidenceLevelPageUrl = '/confidence-level';
  const confidenceLevelPageUrlPattern = new RegExp('/confidence-level(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const confidenceLevelSample = { value: 'Cross-platform RSS Integration', language: 'GREEK' };

  let confidenceLevel;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/confidence-levels+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/confidence-levels').as('postEntityRequest');
    cy.intercept('DELETE', '/api/confidence-levels/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (confidenceLevel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/confidence-levels/${confidenceLevel.id}`,
      }).then(() => {
        confidenceLevel = undefined;
      });
    }
  });

  it('ConfidenceLevels menu should load ConfidenceLevels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('confidence-level');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ConfidenceLevel').should('exist');
    cy.url().should('match', confidenceLevelPageUrlPattern);
  });

  describe('ConfidenceLevel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(confidenceLevelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ConfidenceLevel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/confidence-level/new$'));
        cy.getEntityCreateUpdateHeading('ConfidenceLevel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', confidenceLevelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/confidence-levels',
          body: confidenceLevelSample,
        }).then(({ body }) => {
          confidenceLevel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/confidence-levels+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/confidence-levels?page=0&size=20>; rel="last",<http://localhost/api/confidence-levels?page=0&size=20>; rel="first"',
              },
              body: [confidenceLevel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(confidenceLevelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ConfidenceLevel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('confidenceLevel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', confidenceLevelPageUrlPattern);
      });

      it('edit button click should load edit ConfidenceLevel page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ConfidenceLevel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', confidenceLevelPageUrlPattern);
      });

      it('edit button click should load edit ConfidenceLevel page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ConfidenceLevel');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', confidenceLevelPageUrlPattern);
      });

      it('last delete button click should delete instance of ConfidenceLevel', () => {
        cy.intercept('GET', '/api/confidence-levels/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('confidenceLevel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', confidenceLevelPageUrlPattern);

        confidenceLevel = undefined;
      });
    });
  });

  describe('new ConfidenceLevel page', () => {
    beforeEach(() => {
      cy.visit(`${confidenceLevelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ConfidenceLevel');
    });

    it('should create an instance of ConfidenceLevel', () => {
      cy.get(`[data-cy="value"]`).type('index SSL').should('have.value', 'index SSL');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        confidenceLevel = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', confidenceLevelPageUrlPattern);
    });
  });
});

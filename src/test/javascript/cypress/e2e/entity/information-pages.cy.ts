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

describe('InformationPages e2e test', () => {
  const informationPagesPageUrl = '/information-pages';
  const informationPagesPageUrlPattern = new RegExp('/information-pages(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const informationPagesSample = { title: 'payment', description: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=' };

  let informationPages;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/information-pages+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/information-pages').as('postEntityRequest');
    cy.intercept('DELETE', '/api/information-pages/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (informationPages) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/information-pages/${informationPages.id}`,
      }).then(() => {
        informationPages = undefined;
      });
    }
  });

  it('InformationPages menu should load InformationPages page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('information-pages');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('InformationPages').should('exist');
    cy.url().should('match', informationPagesPageUrlPattern);
  });

  describe('InformationPages page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(informationPagesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create InformationPages page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/information-pages/new$'));
        cy.getEntityCreateUpdateHeading('InformationPages');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', informationPagesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/information-pages',
          body: informationPagesSample,
        }).then(({ body }) => {
          informationPages = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/information-pages+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/information-pages?page=0&size=20>; rel="last",<http://localhost/api/information-pages?page=0&size=20>; rel="first"',
              },
              body: [informationPages],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(informationPagesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details InformationPages page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('informationPages');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', informationPagesPageUrlPattern);
      });

      it('edit button click should load edit InformationPages page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InformationPages');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', informationPagesPageUrlPattern);
      });

      it('edit button click should load edit InformationPages page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InformationPages');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', informationPagesPageUrlPattern);
      });

      it('last delete button click should delete instance of InformationPages', () => {
        cy.intercept('GET', '/api/information-pages/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('informationPages').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', informationPagesPageUrlPattern);

        informationPages = undefined;
      });
    });
  });

  describe('new InformationPages page', () => {
    beforeEach(() => {
      cy.visit(`${informationPagesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('InformationPages');
    });

    it('should create an instance of InformationPages', () => {
      cy.get(`[data-cy="title"]`).type('Nepalese internet').should('have.value', 'Nepalese internet');

      cy.get(`[data-cy="subTitle"]`).type('Account').should('have.value', 'Account');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        informationPages = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', informationPagesPageUrlPattern);
    });
  });
});
